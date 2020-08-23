package com.cadastrodeusuarios.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserLoaderScheduler {

    private static final String inputPath = "files";
    private static final String outputPath = "files-output";
    private static final Set<String> readFiles = new HashSet<>();
    @Autowired
    private LoaderExecutor loaderExecutor;
    @Scheduled(fixedRate = 3000)
    public void load() {
        loaderExecutor.execute(inputPath, outputPath, readFiles);
    }
}
