package shadowpriest.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

/** Keep the printed formula, with a combat-only parenthetical result. */
final class FormulaPreview {
    static void update(AbstractCard card, com.megacrit.cardcrawl.monsters.AbstractMonster target) {
        String result=null;
        boolean handled=true;
        if(inCombat())switch(card.cardID){
            case MindBlast.ID: result=target==null?null:card.damage+" damage, "+(10*shadowpriest.powers.ShadowDotPower.count(target))+" bonus Insanity";break;
            case Insight.ID: result=target==null?null:shadowpriest.powers.ShadowDotPower.count(target)+" cards";break;
            case MindSoothe.ID:
                int min=Integer.MAX_VALUE,max=0;
                for(com.megacrit.cardcrawl.monsters.AbstractMonster m:AbstractDungeon.getMonsters().monsters)if(VoidBolt.alive(m)){
                    int reduction=shadowpriest.powers.ShadowDotPower.count(m)+(card.upgraded?2:1);min=Math.min(min,reduction);max=Math.max(max,reduction);
                }
                result=max==0?null:(min==max?Integer.toString(min):min+"-"+max)+" less damage per hit";break;
            case Bulwark.ID:
                int grants=Bulwark.empowered(AbstractDungeon.player)?2:1;
                result=card.block*grants+" total Block, "+grants+" Blur";break;
            case MaladyOfTheMind.ID: result=target==null?null:(shadowpriest.powers.MadnessPower.stacks(target)*(card.magicNumber-1))+" Madness to apply";break;
            case MadnessOverwhelming.ID: result=((MadnessOverwhelming)card).application()+" Madness";break;
            case VoidTendrils.ID:
                int enemies=0;for(com.megacrit.cardcrawl.monsters.AbstractMonster m:AbstractDungeon.getMonsters().monsters)if(VoidBolt.alive(m))enemies++;
                result=(enemies*card.block)+" total Block";break;
            case ShadowWeaving.ID:
                int dots=0;for(com.megacrit.cardcrawl.monsters.AbstractMonster m:AbstractDungeon.getMonsters().monsters)if(VoidBolt.alive(m))dots+=shadowpriest.powers.ShadowDotPower.count(m);
                result=(dots*card.magicNumber)+" Block at current DoTs";break;
            default:handled=false;
        }
        else handled=card instanceof MindBlast || card instanceof Insight || card instanceof MindSoothe || card instanceof Bulwark || card instanceof MaladyOfTheMind || card instanceof MadnessOverwhelming || card instanceof VoidTendrils || card instanceof ShadowWeaving;
        if(handled)set(card,result);
    }
    static boolean inCombat() {
        return AbstractDungeon.player != null && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null
                && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }
    static void set(AbstractCard card, String result) {
        CardStrings strings = CardCrawlGame.languagePack.getCardStrings(card.cardID);
        String base = card.upgraded && strings.UPGRADE_DESCRIPTION != null
                && !strings.UPGRADE_DESCRIPTION.isEmpty() ? strings.UPGRADE_DESCRIPTION : strings.DESCRIPTION;
        String suffix=result==null||!inCombat()?"":" ("+result+")";
        String text=card instanceof Singularity ? base.replace(" NL Remove",suffix+" NL Remove") : base+(suffix.isEmpty()?"":" NL"+suffix);
        if (!text.equals(card.rawDescription)) { card.rawDescription = text; card.initializeDescription(); }
    }
}
