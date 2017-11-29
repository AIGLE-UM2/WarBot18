package feelsgoodman;

import edu.warbot.agents.agents.WarBase;
import edu.warbot.agents.agents.WarExplorer;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.WarBrain;
import edu.warbot.brains.brains.WarBaseBrain;
import edu.warbot.communications.WarMessage;

public abstract class WarBaseBrainController extends WarBaseBrain {

	WTask ctask;
    private boolean _alreadyCreatedLight;
    private boolean _alreadyCreatedEngi;
    private boolean _alreadyCreatedDouble;
    private boolean _inDanger;
    private int _explos;
    private int _lights;
    private int _engis;
    private int _heaviesAlaunchers;

    public WarBaseBrainController() {
        super();

        _alreadyCreatedLight = false;
        _alreadyCreatedEngi = false;
        _alreadyCreatedDouble = false;
        _inDanger = false;
        _explos = 0;
        _lights = 0;
        _engis = 0;
        _heaviesAlaunchers = 0;
        
        ctask = createPatrouille;
    }

    static WTask handleMsgs = new WTask() {
		String exec(WarBrain bc) {
			return "";
		}
	};
	
	static WTask createPatrouille = new WTask() {
    	String exec(WarBrain bc){
    		WarBaseBrainController me = (WarBaseBrainController) bc;
    		
    		if (!me._alreadyCreatedLight) {
    			if(me._explos < 4){
    				me.setNextAgentToCreate(WarAgentType.WarExplorer);
                    me._explos++;
    			}
    			else{
    				me.setNextAgentToCreate(WarAgentType.WarLight);
    				me._lights++;
    			}
    			
                if(me._lights >= 8) me._alreadyCreatedLight = true;
                return WarBase.ACTION_CREATE;
            }
    		
    		//Si on a fini de créer la patrouille
    		else return(WarBase.ACTION_EAT);
    	}
    };

	static WTask createWarEngineer = new WTask() {
    	String exec(WarBrain bc){
    		WarBaseBrainController me = (WarBaseBrainController) bc;
    		
    		if (!me._alreadyCreatedEngi) {
                me.setNextAgentToCreate(WarAgentType.WarLight);
                me._alreadyCreatedEngi = true;
                return WarBase.ACTION_CREATE;
            }
    		
    		
    		else return(null);
    	}
    };

    @Override
    public String action() {
    	
        for (WarMessage message : getMessages()) {
            if (message.getMessage().equals("Where are you?"))
                reply(message, "I'm here");
        }
        
        
    	String toReturn = ctask.exec(this);

    	if (toReturn == null) {
			return WarExplorer.ACTION_IDLE;
		} else {
			return toReturn;
		}
    	
    	/*if (getNbElementsInBag() >= 0 && getHealth() <= 0.8 * getMaxHealth())
            return WarBase.ACTION_EAT;

        if (getMaxHealth() == getHealth()) {
            _alreadyCreated = true;
        }*/

        

       /* for (WarAgentPercept percept : getPerceptsEnemies()) {
            if (isEnemy(percept) && percept.getType().getCategory().equals(WarAgentCategory.Soldier))
                broadcastMessageToAll("I'm under attack",
                        String.valueOf(percept.getAngle()),
                        String.valueOf(percept.getDistance()));
        }

        for (WarAgentPercept percept : getPerceptsResources()) {
            if (percept.getType().equals(WarAgentType.WarFood))
                broadcastMessageToAgentType(WarAgentType.WarExplorer, "I detected food",
                        String.valueOf(percept.getAngle()),
                        String.valueOf(percept.getDistance()));
        }

        return WarBase.ACTION_IDLE;*/
    }

}
