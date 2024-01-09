package org.algotithmcontestdatacollect.managebackend.Services.ApplicationHandler;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@Service
public class ApplicationHandlerFactory {

    @Autowired
    private ApplicationContext applicationContext;

    public AbstarctHandler getHandler(String type) {
        return switch (type) {
            case "createNewUser" -> applicationContext.getBean(CreateNewUserHandler.class);
            case "addCodeforcesAccount" -> applicationContext.getBean(CodeforcesAccountAddHandler.class);
            case "addAtcoderAccount" -> applicationContext.getBean(AtcoderAccountAddHandler.class);
            case "getAtcoderContestInfo" -> applicationContext.getBean(GetAtcoderContestInfoHandler.class);
            case "getAtcoderAfterSolve" -> applicationContext.getBean(GetAtcoderAfterSolveHandler.class);
            case "getAtcoderSubmit" -> applicationContext.getBean(GetAtcoderSubmitHandler.class);
            default -> null;
        };
    }
}
