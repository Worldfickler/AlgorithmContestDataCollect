package org.algotithmcontestdatacollect.managebackend.Services;

import org.algotithmcontestdatacollect.managebackend.Services.ApplicationHandler.ApplicationHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.algotithmcontestdatacollect.managebackend.Repositories.ApplicationRepository;
import org.springframework.transaction.annotation.Propagation;

@Transactional(propagation = Propagation.REQUIRED)
@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ApplicationHandlerFactory applicationHandlerFactory;


    public void acceptApplication(Long id,String aName) throws Exception {
        var applicationEntity = applicationRepository.findById(id);
        applicationEntity.get().setStatus(Byte.valueOf("1"));
        applicationEntity.get().setAdminName(aName);
        applicationEntity.get().setOperationTime(System.currentTimeMillis());
        applicationHandlerFactory.getHandler(applicationEntity.get().getOpertation()).Handle(applicationEntity.get());
        applicationRepository.save(applicationEntity.get());
        applicationRepository.flush();
    }

    public void rejectApplication(Long id,String aName) {
        var applicationEntity = applicationRepository.findById(id);
        applicationEntity.get().setStatus(Byte.valueOf("2"));
        applicationEntity.get().setAdminName(aName);
        applicationEntity.get().setOperationTime(System.currentTimeMillis());
        applicationRepository.save(applicationEntity.get());
    }
}
