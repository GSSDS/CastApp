package com.mycompany.castapp;

import com.mycompany.castapp.CastApp._CastOnImplBase;

class CastServant extends _CastOnImplBase
{
    public String CastOn()
    {
		System.out.println("Received a call.");
		boolean CastOn = false;
		
		if(!CastOn)
                {
                    CastOn = true;
                }
		
		return "\n Cast Device on " +CastOn+ "";
    }
    
    public String TeleOn()
    {
        System.out.println("Received a call.");
		boolean TeleOn = false;
		
		if(!TeleOn)
                {
                    TeleOn = true;
                }
		
		return "\n Television on " +TeleOn+ "";
    }
}
