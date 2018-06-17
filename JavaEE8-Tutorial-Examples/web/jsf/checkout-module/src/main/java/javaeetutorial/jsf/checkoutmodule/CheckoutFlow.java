package javaeetutorial.jsf.checkoutmodule;

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

    private static final String FLOW_ID = "checkoutFlow";

    @Produces
    @FlowDefinition
    public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {

        flowBuilder.id("", FLOW_ID);

        flowBuilder.viewNode(FLOW_ID, String.format("/%s/%s.xhtml", FLOW_ID, FLOW_ID)).markAsStartNode();
        flowBuilder.returnNode("returnFromCheckoutFlow").fromOutcome("#{checkoutFlowBean.returnValue}");

        flowBuilder.inboundParameter("param1FromJoinFlow", "#{flowScope.param1Value}")
                .inboundParameter("param2FromJoinFlow", "#{flowScope.param2Value}");

        flowBuilder.flowCallNode("calljoin").flowReference("", "joinFlow")
                .outboundParameter("param1FromCheckoutFlow", "#{checkoutFlowBean.name}")
                .outboundParameter("param2FromCheckoutFlow", "#{checkoutFlowBean.city}");

        return flowBuilder.getFlow();
    }
}
