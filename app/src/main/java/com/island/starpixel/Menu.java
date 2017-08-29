package com.island.starpixel;
import android.view.*;
import com.island.*;
import java.io.*;
import android.os.*;
public class Menu extends Finestra
{
	private Gruppo g;
	private String nuovo="NUOVO";
	public void salva(Bundle b)
	{
		b.putBoolean("MENU",true);
		b.putDouble("MENU TRANS",g.transY());
	}
	public void leggi(Bundle b)
	{
		g.trans(0,b.getDouble("MENU TRANS"));
	}
	Menu(Schermo schermo)
	{
		super(schermo);
		g=new Gruppo(this,10,10);
		inizializza();
	}
	void inizializza()
	{
		g.removeAllViews();
		new Bottone(g,1,0,9,1).scrivi(nuovo).setOnClickListener(nuovo());
		String[]file=Memoria.file(schermo().cartella);
		for(int a=0;a<file.length;a++)
		{
			String cartella=schermo().cartella+file[a]+File.separator;
			new Bottone(g,1,a+1,7,a+2).scrivi(Memoria.leggi(cartella+schermo().blocchi.nome)).setOnClickListener(apri(schermo().cartella+file[a]+File.separator));
			new Bottone(g,7,a+1,8,a+2).scrivi("☆").setOnClickListener(modifica(cartella));
			new Bottone(g,8,a+1,9,a+2).scrivi("X").setOnClickListener(cancella(cartella));
		}
		g.aggiorna();
	}
	public MainActivity schermo()
	{
		return(MainActivity)super.schermo();
	}
	private View.OnClickListener nuovo()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				new Nuovo(schermo())
				{
					public void onStop()
					{
						if(g!=null)inizializza();
						super.onStop();
					}
				};
			}
		};
	}
	private View.OnClickListener apri(final String cartella)
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				new Schermata(schermo(),Lan.local(),cartella);
			}
		};
	}
	private View.OnClickListener cancella(final String cartella)
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				final Caricamento c=new Caricamento(schermo(),schermo().lingua.cancellando);
				schermo().thread().post(new Runnable()
				{
					public void run()
					{
						cancella(cartella,c,100);
						c.carica(100);
						schermo().runOnUiThread(new Runnable()
						{
							public void run()
							{
								inizializza();
							}
						});
					}
				});
			}
		};
	}
	private View.OnClickListener modifica(final String cartella)
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				new Modifica(Menu.this,cartella);
			}
		};
	}
	public void sempreGrafico()
	{
		if(libero())
		{
			g.sempre(0.001);
		}
	}
	public static void cancella(String dove,Caricamento c,double parte)
	{
		File file=new File(dove);
		if(file.isDirectory())cancellaDirectory(dove,c,parte);
		else
		{
			file.delete();
			c.carica(parte);
		}
	}
	private static void cancellaDirectory(String dove,Caricamento c,double parte)
	{
		String[]file=Memoria.file(dove);
		if(file!=null)for(int a=0;a<file.length;a++)cancella(dove+"/"+file[a],c,parte/file.length);
		new File(dove).delete();
	}
}
