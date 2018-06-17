This project ORIGINALLY has a bug:

when you hops between the two flows, there could be navigation error,

For example, when you perform the most common action-steps, which is,
 
checkout--> 
submit and complete checkout --> 
subscribe newsletters (and join the club)--> 
then at this step when you click to exit the join flow, wrong page is loaded with message:

"Unable to find matching navigation case with from-view-id '/joinFlow/joinFlow2.xhtml' 
for action 'returnFromJoinFlow' with outcome '/exithome'"

===============
Note that the original file exithome.xhtml has been renamed to exitpage.xhtml in this project
===============
Bug still exists. 
Haven't got time to fix it.




