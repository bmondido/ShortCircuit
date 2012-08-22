package com.attask.jenkins;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;

/**
 * User: brianmondido
 * Date: 8/21/12
 * Time: 2:57 PM
 * <p/>
 * Jenkins Builder to end a build if a variable's value is true
 */
public class ShortCircuitBuilder extends Builder {

    private final String shortCircuitVariable;

    /**
     * Constructor that sets the variable that will be checked during perform()
     *
     * @param shortCircuitVariable
     */
    @DataBoundConstructor
    public ShortCircuitBuilder(String shortCircuitVariable) {
        this.shortCircuitVariable = shortCircuitVariable;
    }

    //TODO how to gracefully end the build

    /**
     * Overridden perform() that throws the ShortCircuit exception to end the build.
     *
     * @param build
     * @param launcher
     * @param listener
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
        String expandedVar = build.getEnvironment(listener).expand(shortCircuitVariable);

        if ("true".equals(expandedVar)) {
            throw new ShortCircuit();
        }
        return true;
    }

    public String getShortCircuitVariable() {
        return shortCircuitVariable;
    }

    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        public DescriptorImpl() {
            load();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Short circuit the build";
        }
    }
}
