import git

def judge_remote_local(repo):
   # remote = repo.rev_parse("origin/HEAD")
    remote = repo.git.ls_remote('origin','HEAD')
    local = repo.rev_parse("HEAD")
    print('hello')
    return remote.split('\t')[0] == local.hexsha

def git_pull(repo):
    repo.remotes.origin.pull()
    return repo.rev_parse("HEAD") == repo.rev_parse("origin/HEAD")

def git_getrepo(path):
    return git.Repo(path)
