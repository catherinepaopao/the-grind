# the-grind
Daily Question App
*Some of the following features require a server that we are too cheap to pay for and are therefore not currently implemented*

Instructions will pop up the first time the app is started, but they can be viewed again in Settings if the user wishes to
There will be a daily question for each subject the app supports, and each subject has a button on the Questions page (server required to push out new question so hardcoded placeholders for now)
Once a question has been viewed, the timer starts running up until they answer correctly, even if the app gets closed
If a question is answered incorrectly, the timer will continue running until the user answers correctly, but the user will be ineligible for a leaderboard spot in that subject for that day
Once the question is answered correctly, the user will be shown their answering time and will be prompted to enter their name (if eligible for leaderboard)
The name will autofill from previous name entries or from the "profile" tab in Settings
The button for the subject will pull up a leaderboard of fastest times for each subject's daily question once the user has finished answering (server required for leaderboard but users get to know their time)
The overall leaderboards will show a leaderboard comparing overall stats (e.g. answering streak), not just daily questions (server required)
Settings page can view instructions, change autofill name, select specific subjects to show (work in progress), and disable sound effects