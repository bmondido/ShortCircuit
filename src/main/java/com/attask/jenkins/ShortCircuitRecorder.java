package com.attask.jenkins;

import hudson.Extension;
import hudson.model.*;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: brianmondido
 * Date: 8/20/12
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShortCircuitRecorder extends Recorder {

    private Result shortCircuitResult;
    @DataBoundConstructor
    public ShortCircuitRecorder(String shortCircuit){
        shortCircuitResult=Result.fromString(shortCircuit);
    }
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }

    @Override
    public boolean perform(AbstractBuild<?, ?> build, hudson.Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
        boolean isBuilding = build.isBuilding();
        System.out.println("HellO!");
        try {
            Field resultField = Run.class.getDeclaredField("result");
            resultField.setAccessible(true);
            resultField.set(build, shortCircuitResult);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        build.save();
        return true;
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
