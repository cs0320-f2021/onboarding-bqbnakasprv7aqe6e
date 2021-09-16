#!/bin/sh

git filter-branch --env-filter '

OLD_EMAIL="71267505+swannyswan@users.noreply.github.com"
CORRECT_NAME="bqbnakasprv7aqe6e"
CORRECT_EMAIL="bqbnakasprv7aqe6e@gmail.com"

if [ "$GIT_COMMITTER_EMAIL" = "$OLD_EMAIL" ]
then
    export GIT_COMMITTER_NAME="$CORRECT_NAME"
    export GIT_COMMITTER_EMAIL="$CORRECT_EMAIL"
fi
if [ "$GIT_AUTHOR_EMAIL" = "$OLD_EMAIL" ]
then
    export GIT_AUTHOR_NAME="$CORRECT_NAME"
    export GIT_AUTHOR_EMAIL="$CORRECT_EMAIL"
fi
' --tag-name-filter cat -- --branches --tags