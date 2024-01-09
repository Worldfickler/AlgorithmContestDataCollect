package org.algotithmcontestdatacollect.managebackend.Services;

import org.algotithmcontestdatacollect.managebackend.Entities.Log;
import org.algotithmcontestdatacollect.managebackend.Repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Service
public class LogService {
    @Autowired
    LogRepository logRepository;
    public void log(String msg) {
        Log log = null;
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            log = Log.createOne(Long.parseLong((String) request.getAttribute("id")),System.currentTimeMillis(),msg, (String) request.getAttribute("username"));
        }catch (Exception E){
            log = Log.createOne(0L,System.currentTimeMillis(),msg,"system");
        }
        logRepository.save(log);
    }
}
