package feelsgoodman;

import java.util.List;

import edu.warbot.agents.agents.WarExplorer;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.agents.resources.WarFood;
import edu.warbot.brains.brains.WarExplorerBrain;

public abstract class WarExplorerBrainController extends WarExplorerBrain {

    public WarExplorerBrainController() {
        super();

    }

    @Override
    public String action() {
		
    	setDebugString("searching food!");  //Retourne la chaine de caractère qui est affichée par l’agent.

        if (isBlocked()) {                   //isBlocked () : Boolean =>Retourne vrai si l’agent est bloqué contre un bord, faux sinon.
            setRandomHeading();            //Change aléatoirement la trajectoire de l’agent.
            return WarExplorer.ACTION_MOVE;    
        }
        
        List<WarAgentPercept> percepts = getPercepts();
        for (WarAgentPercept p : percepts) {

	    	if (p.getType() == WarAgentType.WarFood) {
	    		setDebugString("taking Food");
	            if (p.getDistance() < WarFood.MAX_DISTANCE_TAKE && !isBagFull()) {
	                setHeading(p.getAngle());
	                return WarExplorer.ACTION_TAKE;
	            } else if (!isBagFull()) {
	            	setHeading(p.getAngle());
	                return WarExplorer.ACTION_MOVE;
	            }
	        }

        }        

        return WarExplorer.ACTION_MOVE;
    }
}