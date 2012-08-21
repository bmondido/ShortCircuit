package com.attask.jenkins;

import hudson.model.AbstractProject;
import hudson.model.BuildableItemWithBuildWrappers;
import hudson.model.Descriptor;
import hudson.tasks.BuildWrapper;
import hudson.util.DescribableList;

/**
 * Created with IntelliJ IDEA.
 * User: brianmondido
 * Date: 8/20/12
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildWrapperUtils {
    public static <T extends BuildWrapper> T findBuildWrapper(Class<T> buildWrapper, AbstractProject project) {
        if(project instanceof BuildableItemWithBuildWrappers) {
            return findBuildWrapper(buildWrapper, (BuildableItemWithBuildWrappers)project);
        }
        return null;
    }

    public static <T extends BuildWrapper> T findBuildWrapper(Class<T> buildWrapper, BuildableItemWithBuildWrappers project) {
        DescribableList<BuildWrapper,Descriptor<BuildWrapper>> buildWrappersList = project.getBuildWrappersList();
        for (BuildWrapper wrapper : buildWrappersList) {
            if(buildWrapper.isAssignableFrom(wrapper.getClass())) {
                return buildWrapper.cast(wrapper);
            }
        }
        return null;
    }
}
