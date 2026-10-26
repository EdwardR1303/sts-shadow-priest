package shadowpriest.relics;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
public class ShadowyPreparation extends PriestRelic {
    public static final String ID="shadowpriest:ShadowyPreparation";
    public ShadowyPreparation(){super(ID,RelicTier.COMMON,"shadowpriest/images/cards/icons/shadowy_insight.jpg");}
    @Override public boolean canSpawn(){return super.canSpawn()&&VoidformRelic.current()!=null;}
    @Override public void atBattleStart(){
        VoidformRelic relic=VoidformRelic.current();
        if(relic!=null){int n=(int)AbstractDungeon.getMonsters().monsters.stream().filter(m->!m.isDeadOrEscaped()).count();flash();relic.gainInsanity(10*n);}
    }
    @Override public AbstractRelic makeCopy(){return new ShadowyPreparation();}
}
