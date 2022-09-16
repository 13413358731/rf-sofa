package com.realfinance.sofa.flow.flowable.cmd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.realfinance.sofa.flow.flowable.form.UsersFormType;
import com.realfinance.sofa.flow.util.CommandContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.form.FormData;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.impl.form.DateFormType;
import org.flowable.engine.impl.form.EnumFormType;
import org.flowable.idm.api.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetFormPropertiesJsonCmd implements Command<String>, Serializable {

    private static final Logger log = LoggerFactory.getLogger(GetFormPropertiesJsonCmd.class);

    private FormData formData;

    public GetFormPropertiesJsonCmd(FormData formData) {
        this.formData = formData;
    }

    @Override
    public String execute(CommandContext commandContext) {
        if (formData == null) {
            return null;
        }
        List<FormProperty> formProperties = formData.getFormProperties();
        if (formProperties == null || formProperties.isEmpty()) {
            return null;
        }
        ObjectMapper objectMapper = commandContext.getObjectMapper();
        try {
            List<JsonNode> list = formProperties.stream()
                    .map(e -> {
                        JsonNode jsonNode = objectMapper.convertValue(e, JsonNode.class);
                        if (e.getType() instanceof EnumFormType) {
                            ((ObjectNode) jsonNode.get("type")).putPOJO("values", e.getType().getInformation("values"));
                        } else if (e.getType() instanceof DateFormType) {
                            ((ObjectNode) jsonNode.get("type")).put("datePattern", (String) e.getType().getInformation("datePattern"));
                        } else if (e.getType() instanceof UsersFormType) {
                            if (StringUtils.isNotEmpty(e.getValue())) {
                                String[] split = e.getValue().split(",");
                                ArrayList<String> userIds = new ArrayList<>(split.length);
                                for (String userId : split) {
                                    userIds.add(userId.trim());
                                }
                                List<User> userList = CommandContextUtils.findUser(commandContext,userIds);
                                ArrayNode arrayNode = ((ObjectNode) jsonNode).putArray("valueDisplay");
                                for (User user : userList) {
                                    arrayNode.addObject()
                                            .put("id", Integer.parseInt(user.getId()))
                                            .put("username",user.getFirstName())
                                            .put("realname",user.getLastName());
                                }
                            }
                            ((ObjectNode) jsonNode.get("type"))
                                    .putPOJO("userScope", e.getType().getInformation("userScope"))
                                    .putPOJO("groupScope", e.getType().getInformation("groupScope"))
                                    .putPOJO("minSize", e.getType().getInformation("minSize"))
                                    .putPOJO("maxSize", e.getType().getInformation("maxSize"));
                        }
                        return jsonNode;
                    }).collect(Collectors.toList());
            String jsonStr = objectMapper.writeValueAsString(list);
            return jsonStr;
        } catch (JsonProcessingException e) {
            log.error("解析表单属性异常", e.getCause());
            return null;
        }
    }
}
