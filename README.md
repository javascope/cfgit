# cfgit
1- Extract the classes to C:\ColdFusion2016\cfusion\wwwroot\WEB-INF\classes.

2- restart the Coldfusion server service.

3- cfx_CFGit Custom Tag: pulls all files of defined branch of the defined repository to the defined directory.

4- Sample custom tag usage:
<cfx_CFGit git-dir="c:\catalyst\" 
		  repo-url="https://bitbucket.org/<<user>>/<<branch>>.git" 
      branch="master" 
		  username="<<repository username>>"
		  passwrd="<<repository password>>">

5- git-dir: the base directory to execute git commands.
    repo-url: git repository url formatted https.
    branch: the branch of the defined repository to pull.
    username: username having the necessary permissions to access the repository
    passwrd: password
    sepBranch: if you set the value to "1", then new directory named by the branch name is created and later pulled the files to there.
