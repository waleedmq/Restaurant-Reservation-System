public class CmdStartNewDay extends RecordedCommand{
    String date;
    String previousDate=SystemDate.getInstance().toString();
    
    public void execute(String[] cmdParts) {
    	try {
			if(cmdParts.length<2)
				throw new ExInsufficientCommandArguments();
			date=cmdParts[1];
			SystemDate.getInstance().set(date);
			addUndoCommand(this);
			clearRedoList();
			System.out.println("Done.");
		} catch (ExInsufficientCommandArguments e) {
			System.out.println(e.getMessage());
		}
    }
    @Override
    public void undoMe() {
        SystemDate.getInstance().set(previousDate);
        addRedoCommand(this);
    }
    @Override
    public void redoMe() {
        SystemDate.getInstance().set(date);
        addUndoCommand(this);
    }
}