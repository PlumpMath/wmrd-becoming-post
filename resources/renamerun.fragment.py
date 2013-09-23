ga = glob(inp+"/iteration_*/full_a.jpg")
ga.sort()
gb = glob(inp+"/iteration_*/full_b.jpg")
gb.sort()
gc = glob(inp+"/iteration_*/full_c.jpg")
gc.sort()

upper = Math.min(len(ga), Math.min(len(gb), len(gc)))
run = file(out+"/runner.sh", "w")

for n in range(upper):
	f = gc[n]
	t = out+"/c_%04i.jpg" % n
	print>>run, "cp %(f)s %(t)s"% locals()

for n in range(upper):
	f = gb[n]
	t = out+"/b_%04i.jpg" % n
	print>>run, "cp %(f)s %(t)s"% locals()

for n in range(upper):
	f = ga[n]
	t = out+"/a_%04i.jpg" % n
	print>>run, "cp %(f)s %(t)s"% locals()

run.close()

ec = ExecuteCommand(out, ["/bin/bash", out+"/runner.sh"], 1)
ec.waitFor(1)

ec = ExecuteCommand(out, ["/bin/bash", source+"/produceSeq.sh"], 1)
ec.waitFor(1)

ec = ExecuteCommand(out, ["/bin/bash", source+"/genavi"], 1)
ec.waitFor(1)

print out
