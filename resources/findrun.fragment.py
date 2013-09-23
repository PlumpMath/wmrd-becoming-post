from java.io import File
from glob import glob

source = _self.screenshotRoot

sources = glob(source+"/run_*")
sources.sort()

biggest = 0
biggestAt = ""
for n in range(len(sources)-1, len(sources)-10, -1):
	shots = glob(source+"/run_%08i/shot_*" % n)
	for q in shots:
		iters = glob(q+"/iter*")
		if (len(iters)>biggest):
			biggest = len(iters)
			biggestAt = q
			if (biggest>30):
				break


print biggestAt
print biggest

inp = biggestAt
out = biggestAt+"/bundle"

File(out).mkdirs()

_self.find["renamerun"]()


ExecuteCommand(out, ["/bin/mv", out+"/seq.avi", out+"/seq_%s_%s.avi" %(out.split("/")[-2], out.split("/")[-3])], 1).waitFor()

S.quitLater()
