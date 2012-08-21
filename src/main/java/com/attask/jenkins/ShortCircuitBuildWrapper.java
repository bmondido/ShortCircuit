package com.attask.jenkins;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.*;
import hudson.tasks.BuildWrapper;
import hudson.tasks.BuildWrapperDescriptor;
import hudson.tasks.Recorder;
import hudson.tasks.junit.TestResult;
import hudson.util.FormValidation;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.RequestImpl;
import org.kohsuke.stapler.StaplerRequest;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: brianmondido
 * Date: 8/17/12
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShortCircuitBuildWrapper extends BuildWrapper{

    private Result shortCircuitResult;
    @DataBoundConstructor
    public ShortCircuitBuildWrapper(String shortCircuit) {
        shortCircuitResult=Result.fromString(shortCircuit);
    }


    @Override
    public Environment setUp(AbstractBuild build, Launcher launcher, BuildListener listener) throws IOException, InterruptedException {
        throw new NumberFormatException("Ungracefully short-circuiting the build");
    }

    @Override
    public DescriptorImpl getDescriptor(){
        return (DescriptorImpl)super.getDescriptor();
    }

    public Result getShortCircuitResult() {
        return shortCircuitResult;
    }

    @Extension
    public static class DescriptorImpl extends BuildWrapperDescriptor {
        private boolean useShortCircuit;

        public FormValidation doCheckVariable(@QueryParameter String value){
            if(value.isEmpty()){
                return FormValidation.error("Please set a variable");
            }
            return FormValidation.ok("Cool Beans!");
        }

        @Override
        public boolean isApplicable(AbstractProject<?, ?> item) {
            return true;
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
            useShortCircuit=json.getBoolean("shortCircuit");
            save();
            return super.configure(req,json);
        }

        public boolean getShortCircuit(){
            return useShortCircuit;
        }

        @Override
        public String getDisplayName() {
            return "Short Circuit";
        }
    }
}
