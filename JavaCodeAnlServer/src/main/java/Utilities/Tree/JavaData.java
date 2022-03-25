/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


/**
 *
 * @author jefemayoneso
 */
public class JavaData {

    private String variable;
    private String type;
    private ArrayList<String> attributes;
    private String value;
    private ArrayList<JavaData> parameters;
    private boolean isMethod;

    public JavaData() {
        this(null, null);
    }

    public JavaData(String variable, String type) {
        this.variable = variable;
        this.type = type;
        this.attributes = new ArrayList<>();
        this.parameters = null;
        this.isMethod = false;
    }

    public void addAttributes(String... attributes) {
        this.attributes.addAll(Arrays.asList(attributes));
    }

    @Override
    public String toString() {
        String attributes = "";
        String attributesk = this.attributes == null ? ""
                : this.attributes.stream().map(attribute -> attribute + ",").reduce(attributes, String::concat);
        return "JavaData [attributes=" + attributesk + ", type=" + type + ", value=" + value + ", variable=" + variable
                + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JavaData other = (JavaData) obj;
        // check name
        if (!variable.equals(other.variable)) {
            return false;
        } else { // same name, check type
            if ("FILE".equals(type) || "MASTER".equals(type)) {
                return false;
            } else if ("FILE".equals(other.type) || "MASTER".equals(other.type)) {
                return false;
            } else if (!type.equals(other.type)) { // no same type
                return false;
            } else {
                // check parameters
                if (parameters == null && other.parameters != null) {
                    return false;
                } else if (parameters != null && other.parameters == null) {
                    return false;
                } else if (parameters != null && other.parameters != null) {
                    if (parameters.size() != other.parameters.size()) {
                        return false;
                    } else {
                        int mattches = 0;
                        for (JavaData param : parameters) {
                            if (other.getParameters().contains(param)) {
                                mattches++;
                            }
                        }
                        return mattches == parameters.size();
                    }
                }
            }
        }
        return true;

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.variable);
        hash = 79 * hash + Objects.hashCode(this.type);
        return hash;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<String> attributes) {
        this.attributes = attributes;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ArrayList<JavaData> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<Node<JavaData>> parameters) {
        if (parameters != null) {
            this.parameters = new ArrayList<>();
            parameters.forEach(node -> {
                this.parameters.add(node.getData());
            });
        }
    }

    public boolean isIsMethod() {
        return isMethod;
    }

    public void setIsMethod(boolean isMethod) {
        this.isMethod = isMethod;
    }

    public int getParametersSize() {
        try {
            return this.parameters.size();
        } catch (Exception e) {
            return 0;
        }
    }

}
