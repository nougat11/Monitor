package com.example.grandy;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

public class UpdateExecutor implements Executor {
    public void execute(Runnable r) {
        r.run();
    }
}
