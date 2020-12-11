package com.example.server;

import com.example.model.LocalStore;
import com.example.model.RestRequest;
import com.example.model.TestResource;
import com.example.view.ConsolePrinter;

import java.util.List;

public class RestServiceParser {
    private LocalStore  localStore = LocalStore.getIstance();

    public boolean push(RestRequest restRequest, String command) {
        try {
            int id = Integer.parseInt(restRequest.getParams().get(0));
            String name = restRequest.getParams().get(1);
            String description = restRequest.getParams().subList(2, restRequest.getParams().size())
                    .stream().reduce((x,y)->x+" "+y).get();
            if(localStore.getResource(id)!=null)
                localStore.removeResource(id);
            TestResource resource = new TestResource(id, name, description);
            localStore.addResource(resource);
            print(command, resource);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<TestResource> getAll(String command) {
        print(command, localStore.getResources());
        return localStore.getResources();
    }

    public TestResource get(RestRequest restRequest, String command) {
        int id = Integer.parseInt(restRequest.getParams().get(0));
        return localStore.getResource(id);
    }

    public boolean remove(RestRequest restRequest, String command) {
        int id = Integer.parseInt(restRequest.getParams().get(0));
        print(command, id);
        return localStore.removeResource(id);
    }

    private void print (String command, Object t) {
        ConsolePrinter.printServer(command + " -> " + t.toString());
        ConsolePrinter.printServer(localStore.toString());
    }
}
