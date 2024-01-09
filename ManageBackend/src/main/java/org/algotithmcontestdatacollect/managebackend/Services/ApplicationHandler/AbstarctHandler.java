package org.algotithmcontestdatacollect.managebackend.Services.ApplicationHandler;

import org.algotithmcontestdatacollect.managebackend.Entities.Application;


public abstract class AbstarctHandler {
    public abstract void Handle(Application entity) throws Exception;
}
