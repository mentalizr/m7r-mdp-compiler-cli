package org.mentalizr.contentManagerCli.console;

public class OutputFormatterBuilder {

    private OutputFormatter.OutputType outputType;
    private String tag;
    private boolean hasProgramTag;

    public OutputFormatterBuilder() {
        this.outputType  = OutputFormatter.OutputType.NORMAL;
        this.tag = "";
        this.hasProgramTag = false;
    }

    public OutputFormatterBuilder withTypeOK() {
        this.outputType = OutputFormatter.OutputType.OK;
        this.tag = "OK";
        return this;
    }

    public OutputFormatterBuilder withTypeError() {
        this.outputType = OutputFormatter.OutputType.ERROR;
        this.tag = "Error";
        return this;
    }

    public OutputFormatterBuilder withTypeInternalError() {
        this.outputType = OutputFormatter.OutputType.ERROR;
        this.tag = "Internal error";
        return this;
    }
    public OutputFormatterBuilder withTypeNormal() {
        this.outputType = OutputFormatter.OutputType.NORMAL;
        this.tag = "";
        return this;
    }

    public OutputFormatterBuilder withProgramTag() {
        this.hasProgramTag = true;
        return this;
    }

    public OutputFormatter build() {
        return new OutputFormatter(
                this.outputType,
                this.tag,
                this.hasProgramTag
        );
    }

}
