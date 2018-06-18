package javaeetutorial.jsf.facesflows;

import java.io.Serializable;
import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;

/**
 * This class defines the flow "joinFlow"
 */
public class JoinFlow implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String DIR = "faces-flows-example";
    private static final String SUB_DIR = "joinFlow";
    private static final String FLOW_ID = "joinFlow";
    
    private static final String VIEW_NODE_1_ID = "joinFlow1";
    private static final String VIEW_NODE_2_ID = "joinFlow2";

    private static final String FLOW_CALL_NODE_ID = "callCheckoutFlow";
    private static final String CHECKOUT_FLOW_ID = "checkoutFlow";

    private static final String JOIN_RETURN_NODE_ID = "joinReturnNode";

    @Produces
    @FlowDefinition
    public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {

        flowBuilder.id("", FLOW_ID);

        flowBuilder.viewNode(VIEW_NODE_1_ID, String.format("/%s/%s/%s.xhtml", DIR, SUB_DIR, VIEW_NODE_1_ID)).markAsStartNode();
        flowBuilder.viewNode(VIEW_NODE_2_ID, String.format("/%s/%s/%s.xhtml", DIR, SUB_DIR, VIEW_NODE_2_ID));

        flowBuilder.inboundParameter("param1FromCheckoutFlow", "#{flowScope.param1Value}")
                .inboundParameter("param2FromCheckoutFlow", "#{flowScope.param2Value}").flowCallNode(FLOW_CALL_NODE_ID)
                .flowReference("", CHECKOUT_FLOW_ID).outboundParameter("param1FromJoinFlow", "joinFlow param1 value")
                .outboundParameter("param2FromJoinFlow", "joinFlow param2 value");
        
        flowBuilder.returnNode(JOIN_RETURN_NODE_ID).fromOutcome("#{joinFlowBean.returnValue}");
        
        return flowBuilder.getFlow();
    }
}
