package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;

public class VoidEntropy extends PriestCard {
    public static final String ID="shadowpriest:VoidEntropy";
    public VoidEntropy() { super(ID,2,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF); baseMagicNumber=magicNumber=2; }
    @Override public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new AbstractGameAction(){public void update(){
            shadowpriest.relics.VoidformRelic r=shadowpriest.relics.VoidformRelic.current();
            if(r!=null)r.enterImmediately();
            isDone=true;
        }});
        addToBot(new DrawCardAction(p,magicNumber)); }
    @Override public void upgrade() { if(!upgraded) { upgradeName(); upgradeMagicNumber(1); } }
    
}
