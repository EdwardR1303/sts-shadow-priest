package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;

public class TwinSuns extends PriestCard {
    public static final String ID="shadowpriest:TwinSuns";
    public TwinSuns() { super(ID,1,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.ENEMY); baseMagicNumber=magicNumber=0; }
    @Override public void use(AbstractPlayer p, AbstractMonster m) { int strength=m.hasPower("Strength")?m.getPower("Strength").amount:0;
        if(strength>0)addToBot(new ApplyPowerAction(p,p,new HastePower(p,strength+magicNumber),strength+magicNumber)); }
    @Override public void upgrade() { if(!upgraded) { upgradeName(); upgradeMagicNumber(1);upgradeText(); } }
    
}
