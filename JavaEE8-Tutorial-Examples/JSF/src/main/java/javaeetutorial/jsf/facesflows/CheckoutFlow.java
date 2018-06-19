package javaeetutorial.jsf.facesflows;

import java.io.Serializable;
import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;

/**
 * This class defines the flow "checkoutFlow"
 */
public class CheckoutFlow implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String DIR = "faces-flows-example";
    private static final String SUB_DIR = "checkoutFlow";
    
    private static final String FLOW_ID = "checkoutFlow";
    private static final String VIEW_NODE_1_ID = "checkoutFlow1";
    private static final String VIEW_NODE_2_ID = "checkoutFlow2";
    private static final String VIEW_NODE_3_ID = "checkoutFlow3";
    private static final String VIEW_NODE_4_ID = "checkoutFlow4";

    private static final String FLOW_CALL_NODE_ID = "callJoinFlow";
    private static final String JOIN_FLOW_ID = "joinFlow";

    private static final String CHECKOUT_RETURN_NODE_ID = "checkoutReturnNode";

    @Produces
    @FlowDefinition
    public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {

        flowBuilder.id("", FLOW_ID);

        flowBuilder.viewNode(VIEW_NODE_1_ID, String.format("/%s/%s/%s.xhtml", DIR, SUB_DIR, VIEW_NODE_1_ID)).markAsStartNode();

        flowBuilder.viewNode(VIEW_NODE_2_ID, String.format("/%s/%s/%s.xhtml", DIR, SUB_DIR, VIEW_NODE_2_ID));
        flowBuilder.viewNode(VIEW_NODE_3_ID, String.format("/%s/%s/%s.xhtml", DIR, SUB_DIR, VIEW_NODE_3_ID));
        flowBuilder.viewNode(VIEW_NODE_4_ID, String.format("/%s/%s/%s.xhtml", DIR, SUB_DIR, VIEW_NODE_4_ID));

        flowBuilder.inboundParameter("param1FromJoinFlow", "#{flowScope.param1Value}")
                .inboundParameter("param2FromJoinFlow", "#{flowScope.param2Value}").flowCallNode(FLOW_CALL_NODE_ID)
                .flowReference("", JOIN_FLOW_ID).outboundParameter("param1FromCheckoutFlow", "#{checkoutFlowBean.name}")
                .outboundParameter("param2FromCheckoutFlow", "#{checkoutFlowBean.city}");

        flowBuilder.returnNode(CHECKOUT_RETURN_NODE_ID).fromOutcome("#{checkoutFlowBean.returnValue}");

        return flowBuilder.getFlow();
    }
}
