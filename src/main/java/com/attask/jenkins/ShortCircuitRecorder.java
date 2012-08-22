package com.attask.jenkins;

import hudson.EnvVars;
import hudson.Extension;
import hudson.model.*;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * User: brianmondido
 * Date: 8/20/12
 * Time: 2:50 PM
 * <p/>
 * Jenkins Recorder to set the result of a short circuited build
 */
public class ShortCircuitRecorder extends Recorder {

    private Result shortCircuitResult;
    private String shortCircuitVariable;

    /**
     * Constructs a Recorder to specify the build result
     *
     * @param shortCircuit
     * @param shortCircuitVariable
     */
    @DataBoundConstructor
    public ShortCircuitRecorder(String shortCircuit, String shortCircuitVariable) {
        shortCircuitResult = Result.fromString(shortCircuit);
        this.shortCircuitVariable = shortCircuitVariable;
    }

    /**
     * Sets the short circuited build's result to the recorder's shortCircuitResult
     *
     * @param build
     * @param launcher
     * @param listener
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
    @Override
    public boolean perform(AbstractBuild<?, ?> build, hudson.Launcher launcher, BuildListener listener) throws InterruptedException, IOException {

        EnvVars environment = build.getEnvironment(listener);
        String expand = environment.expand(shortCircuitVariable);
        if ("true".equals(expand)) {
            try {
                Field resultField = Run.class.getDeclaredField("result");
                resultField.setAccessible(true);
                resultField.set(build, getShortCircuitResult());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            build.save();
        }
        return true;
    }

    public Result getShortCircuitResult() {
        return shortCircuitResult;
    }

    public String getShortCircuitVariable() {
        return shortCircuitVariable;
    }

    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }

    @Extension
    public static class ShortCircuitRecorderDescriptor extends BuildStepDescriptor<Publisher> {
        public ShortCircuitRecorderDescriptor() {
            super(ShortCircuitRecorder.class);
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Auto Override Build Results";
        }
    }
}
