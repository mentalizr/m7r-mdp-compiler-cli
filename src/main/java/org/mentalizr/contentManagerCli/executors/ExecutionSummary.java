package org.mentalizr.contentManagerCli.executors;

public class ExecutionSummary {

    private final String operation;
    private int successfulExecutions;
    private int failedExecutions;

    public ExecutionSummary(String operation) {
        this.operation = operation;
        this.successfulExecutions = 0;
        this.failedExecutions = 0;
    }

    public String getOperation() {
        return this.operation;
    }

    public void incSuccess() {
        this.successfulExecutions++;
    }

    public void incFailed() {
        this.failedExecutions++;
    }

    public int getSuccessfulExecutions() {
        return this.successfulExecutions;
    }

    public int getFailedExecutions() {
        return this.failedExecutions;
    }

    public int getTotalExecutions() {
        return this.successfulExecutions + this.failedExecutions;
    }

    public boolean isSuccessful() {
        return this.failedExecutions == 0;
    }

    public boolean isFailed() {
        return this.failedExecutions > 0;
    }
}
