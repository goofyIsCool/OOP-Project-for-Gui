package Program;

import Container.*;

public class IrresponsibleSenderWithDangerousGoods extends Exception{

    public IrresponsibleSenderWithDangerousGoods(String msg){
        super(msg);
    }

    public static void checkIf(Container c, int time) throws IrresponsibleSenderWithDangerousGoods {
        int timeOfContainerInWarehouse = ThreadTimer.getSeconds() - time;

        if (timeOfContainerInWarehouse > 5*5 && c instanceof ExplosiveCargo)
            throw new IrresponsibleSenderWithDangerousGoods(ConsoleColors.RED + "\nWarning! ExplosiveContainer (id=" + c.getId() + ") in the warehouse got disposed!" + ConsoleColors.RESET);
        else if (timeOfContainerInWarehouse  > 5*10 && c instanceof ToxicLiquidContainer)
            throw new IrresponsibleSenderWithDangerousGoods(ConsoleColors.RED + "\nWarning! ToxicCargo Liquid (id=" + c.getId() + ") in the warehouse got disposed!" + ConsoleColors.RESET);
        else if (timeOfContainerInWarehouse > 5*14 && c instanceof ToxicPowderyCargo) //Change
            throw new IrresponsibleSenderWithDangerousGoods(ConsoleColors.RED + "\nWarning! ToxicCargo Powdery (id=" + c.getId() + ") in the warehouse got disposed!" + ConsoleColors.RESET);
    }
}
