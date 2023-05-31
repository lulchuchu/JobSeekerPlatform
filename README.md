# SocialPlatformForJobSeeker
 ## Description:
Social media platform helps user communicate and find desirale job.
Users can update their profile page information like profile picture, name, ... and upload CV. <br/>
Users can create a new post, people can like or comment to that post, the user posting that will receive notifications.<br/>
Users can follow others users and look at theirs job experience, post created, currently working company, ....<br/>
Users can also message to another user and receive real time notification about the following's activity.<br/>
Users can look for currently opening jobs, filter suitable jobs and apply for jobs<br/>
The manager of a company can manage opening job of that company, and manage applicant: set interview time for applicants or reject applicants, when done, applicant will receive notification of result<br/>
## Detail:
Using Spring boot to create project <br/>
Using Spring Data JPA to connect to MySQL database for fetching and updating data <br/>
Using Spring Security and JWT for User authentication and authorization <br/>
Using Websocket to implement real time chat feature and notification <br/>
### Login page
![image](https://github.com/lulchuchu/JobSeekerPlatform/assets/31687664/cb62d28d-1347-47a6-95e9-785183b3280a)
User will login to the web using this page<br/>
### Home page
![image](https://github.com/lulchuchu/JobSeekerPlatform/assets/31687664/efd0bb2f-8199-4192-8bdf-1c7a46d01d3d)
User can create new post, add photo to the post and see the post created by following person and like,comment to that post<br/>
### Profile page
![image](https://github.com/lulchuchu/JobSeekerPlatform/assets/31687664/8d537c1a-c188-4d0c-be54-c995ecb36cdc)
![image](https://github.com/lulchuchu/JobSeekerPlatform/assets/31687664/d673f2d7-952d-4286-9027-b2ee10d1926a)
This is user profile page, it displays user's infomation like name, profile picture, email,post created, ....<br/>
User can click Add profile button to update user profile, upload CV to upload CV<br/>
### Job page
![image](https://github.com/lulchuchu/JobSeekerPlatform/assets/31687664/07c0ce38-acb8-4ac0-bd84-aa6572808608)
This is openning job page, on the left side displays short info about the job, the right side about job details will show up if user clicked to a job on the left<br/>
There is a apply button for user to apply to the job<br/>
If user is a manager of a company, there will be View applicants button, manager can click to that button to display all applicant applied to that job<br/>
There is a buttong list on top to filter suitable job for user.<br/>
### Company page
![image](https://github.com/lulchuchu/JobSeekerPlatform/assets/31687664/92811e91-180a-4fd7-a0ca-39f497c65354)
This is company page, it displays basic information about the company, user can follow company to keep up with latest post, if user is manager, there will be a Openingg jobs button to display opening jobs of that company<br/>
### Manage job page
![image](https://github.com/lulchuchu/JobSeekerPlatform/assets/31687664/a07b95f1-8f74-4d36-a7d4-d71ee83669ec)
This page will display all opening job of a company in table, click a row to display all applicant applied<br/>
![image](https://github.com/lulchuchu/JobSeekerPlatform/assets/31687664/66af5f3f-a792-456d-86c6-49ad4947b247)
This page will display all user applied for a job, there is a choose button to set interview and reject buttong to reject applicant<br/>
![image](https://github.com/lulchuchu/JobSeekerPlatform/assets/31687664/6dc2b54b-06a8-477d-9101-8956f1900c99)
The manager can set interview time using this pop up<br/>
![image](https://github.com/lulchuchu/JobSeekerPlatform/assets/31687664/8c49fb4f-f96f-4b71-81ff-b4789b43af71)<br/>
User will receive this notification real time after manager set interview time<br/>
### Messaging page
![image](https://github.com/lulchuchu/JobSeekerPlatform/assets/31687664/1179e6af-4c52-4fb8-8bbc-319e7f203d57)
This is messaging page, user can message with following people, the message will display real time.<br/>
