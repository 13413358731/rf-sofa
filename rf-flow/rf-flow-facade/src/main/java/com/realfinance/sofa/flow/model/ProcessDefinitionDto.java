package com.realfinance.sofa.flow.model;

public class ProcessDefinitionDto {

    private String id;
    private String key;
    private String name;
    private String deploymentId;
    private Integer version;
    private Boolean suspended;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    @Override
    public String toString() {
        return "ProcessDefinitionDto{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", deploymentId='" + deploymentId + '\'' +
                ", version=" + version +
                ", suspended=" + suspended +
                '}';
    }
}
