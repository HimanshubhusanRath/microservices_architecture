# Follow the below steps :
1. Create a channel in Slack
2. Use the below link to create a Slack App, enable incoming webhooks for the given channel in this app.
   - https://api.slack.com/messaging/webhooks
3. From the Spring application, we need to send message to this App's endpoint (webhook url). This app will eventually post the message to the slack channel.
4. Run the spring application and use the below endpoint to test the end to end flow.
   - http://localhost:8080/user/greet?message=hello
5. Now we can check the same message (hello) being posted to the slack channel.
