package business;

import org.apache.commons.lang.RandomStringUtils;

import dao.IAdminOperatorDAO;
import po.*;

public class AdminOperatorImp implements IAdminOperator
{
	private IAdminOperatorDAO adminoperator;

	public String addNumber(String nbtype,String StartMobile, String endMobile)
	{
		String message = "";

		//retrieve the first 2 digits
		String before = StartMobile.substring(0,2);

		//casting into int
		int a1 = Integer.parseInt(StartMobile.substring(2,11));
		int a2 = Integer.parseInt(endMobile.substring(2,11));

		int all = 0;
		for(int i = a1;i <= a2;i++)
		{
			if(!adminoperator.isMobileExist(before + i))
			{
				// generate sim number
				String cardnumber = RandomStringUtils.randomNumeric(20);

				while(adminoperator.isCardExist(cardnumber))
				{
					cardnumber = RandomStringUtils.randomNumeric(20);
				}

				// construct new mobile
				Tmobiles mobile = new Tmobiles();
				mobile.setCardNumber(cardnumber);
				mobile.setMobileType(nbtype);
				mobile.setIsAvailable("Y");
				mobile.setMobileNumber(before + i);

				if(adminoperator.addNumber(mobile))
				{
					all++;
				}
			}
		}
		message = all+" mobile numbers are added successfully.";
		return message;
	}

	public String addOperator(Toperator operator)
	{
		String message = "";
		if(!adminoperator.isOperatorExist(operator))
		{
			if(adminoperator.addOperator(operator))
			{
				message = "Operator added successfully.";
			}else{
				message = "Adding operator fails; Please try later";
			}
		}else{
			message = "Operator already exists!";
		}
		return message;
	}


	public String editChargeRule(String rule,String[] chargestr)
	{
		String message = "";
		if(adminoperator.delAllChargeRule(rule))
		{
			
			for(int i = 0;i < chargestr.length;i++)
			{
				TchargeRule myrule = new TchargeRule();
				TchargeRuleId id = new TchargeRuleId();
				Tcharge charge = new Tcharge();
				charge.setChargeCode(chargestr[i]);
				id.setFuncId(rule);
				id.setFuncName("Open Account");
				id.setTcharge(charge);
				myrule.setId(id);
				myrule.setTcharge(charge);
				//System.out.println(chargestr[i]);
				if(!adminoperator.addChargeRule(myrule)){
					message = "Fail to set up!";
				}else{
					message = "Setting up succeeds!";
				}
			}
		}else{
			message = "Error deleting record!";
		}
		return message;
	}


	public String setMoney(Tcharge charge)
	{
		String message = "";
		if(adminoperator.setMoney(charge))
		{
			message = "Setting up succeeds!";
		}else{
			message = "Fail to set up! Try again!";
		}
		return message;
	}
	
	public IAdminOperatorDAO getAdminoperator()
	{
		return adminoperator;
	}

	public void setAdminoperator(IAdminOperatorDAO adminoperator)
	{
		this.adminoperator = adminoperator;
	}

}
