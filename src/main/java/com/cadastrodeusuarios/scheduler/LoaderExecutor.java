package com.cadastrodeusuarios.scheduler;

import java.util.Set;

public abstract class LoaderExecutor {
    abstract void execute(String inputPath, String outputPath, Set<String> readFiles);
}
