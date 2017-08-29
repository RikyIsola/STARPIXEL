package com.island.starpixel;
import android.graphics.*;
import android.os.*;
import android.view.*;
import com.island.*;
import java.io.*;
import java.net.*;
public class Nuovo extends Finestra
{
	private Testo nome,seme,semetitolo,nometitolo;
	private Bottone selezionato,selezionato2,modalita1,modalita2,crea,multyplayer;
	private Gruppo collegamenti,g;
	private int index=0;
	Nuovo(Schermo schermo)
	{
		super(schermo);
		g=new Gruppo(this,10,13);
		nometitolo=new Testo(g,1,1,9,2,schermo().lingua.nome);
		nome=new Testo(g,1,2,9,3,"",Color.BLACK,Color.RED);
		nome.setOnClickListener(scrivi());
		semetitolo=new Testo(g,1,4,9,5,schermo().lingua.seme);
		seme=new Testo(g,1,5,9,6,"",Color.BLACK,Color.GREEN);
		seme.setOnClickListener(scrivi());
		selezionato=new Bottone(g,1,7,4,8,Color.DKGRAY,Color.LTGRAY);
		modalita1=selezionato;
		selezionato.scrivi(schermo().lingua.creativa).larghezzaX(0.6).setOnClickListener(modalita());
		collegamenti=new Gruppo(g,0,4,10,8,10,4);
		collegamenti.setVisibility(View.INVISIBLE);
		modalita2=new Bottone(g,6,7,9,8);
		modalita2.scrivi(schermo().lingua.soppravivenza).larghezzaX(0.6).setOnClickListener(modalita());
		selezionato2=new Bottone(g,1,9,4,10,Color.DKGRAY,Color.LTGRAY);
		selezionato2.scrivi(schermo().lingua.crea).larghezzaX(0.6).setOnClickListener(multyplayer());
		multyplayer=new Bottone(g,6,9,9,10);
		multyplayer.scrivi(schermo().lingua.unisciti).larghezzaX(0.6).setOnClickListener(multyplayer());
		crea=new Bottone(g,3.5,11,6.5,12);
		crea.scrivi(schermo().lingua.crea).setOnClickListener(crea());
		g.aggiorna();
	}
	public void salva(Bundle b)
	{
		b.putBoolean("NUOVO",true);
		b.putString("NUOVO NOME",nome.testo().toString());
		b.putString("NUOVO SEME",seme.testo().toString());
		b.putBoolean("NUOVO MODALITA",selezionato.testo().equals(modalita1.testo()));
		b.putBoolean("NUOVO MULTYPLAYER",crea.testo()==schermo().lingua.crea);
	}
	public void leggi(Bundle b)
	{
		if(!b.getBoolean("NUOVO MODALITA"))modalita2.performClick();
		if(!b.getBoolean("NUOVO MULTYPLAYER"))multyplayer.performClick();
		nome.scrivi(b.getString("NUOVO NOME"));
		seme.scrivi(b.getString("NUOVO SEME"));
	}
	public MainActivity schermo()
	{
		return(MainActivity)super.schermo();
	}
	private View.OnClickListener scrivi()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				final Testo t=(Testo)p1;
				Tastiera tas=new Tastiera(schermo(),t.colore(),t.testo())
				{
					protected void conferma(StringBuilder testo)
					{
						t.scrivi(testo);
					}
				};
				if(t.colore()==Color.GREEN)tas.versioneNumerica();
			}
		};
	}
	private View.OnClickListener modalita()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				selezionato.colore(Color.LTGRAY,Color.DKGRAY);
				selezionato=(Bottone)p1;
				selezionato.colore(Color.DKGRAY,Color.LTGRAY);
			}
		};
	}
	private View.OnClickListener crea()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				Bottone b=(Bottone)p1;
				if(b.testo()==schermo().lingua.crea)Generatore.nuovo(schermo(),nome.testo().toString(),seme.testo().toString(),selezionato.testo().equals(modalita1.testo()));
				else new Schermata(schermo(),nome.testo().toString(),null);
				cancel();
			}
		};
	}
	public void sempreGrafico()
	{
		collegamenti.aggiorna();
	}
	private View.OnClickListener multyplayer()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				Bottone b=(Bottone)p1;
				if(selezionato2!=b)
				{
					if(b.testo()==schermo().lingua.unisciti)
					{
						semetitolo.setVisibility(View.INVISIBLE);
						modalita1.setVisibility(View.INVISIBLE);
						modalita2.setVisibility(View.INVISIBLE);
						seme.setVisibility(View.INVISIBLE);
						collegamenti.setVisibility(View.VISIBLE);
						collegamenti.removeAllViews();
						g.aggiorna();
						index=0;
						String ip=Lan.ip(schermo());
						if(ip.charAt(1)!="9".toCharArray()[0])ip="192.168.43.0";
						ip=ip.substring(0,ip.lastIndexOf(".")+1);
						final String fip=ip;
						new Processo()
						{
							public void esegui()
							{
								for(int a=0;a<50&&collegamenti.getVisibility()==View.VISIBLE;a++)ricerca(fip+a);
								toast(fip);
							}
						};
						new Processo()
						{
							public void esegui()
							{
								for(int a=50;a<100&&collegamenti.getVisibility()==View.VISIBLE;a++)ricerca(fip+a);
							}
						};
						new Processo()
						{
							public void esegui()
							{
								for(int a=100;a<150&&collegamenti.getVisibility()==View.VISIBLE;a++)ricerca(fip+a);
							}
						};
						new Processo()
						{
							public void esegui()
							{
								for(int a=150;a<200&&collegamenti.getVisibility()==View.VISIBLE;a++)ricerca(fip+a);
							}
						};
						new Processo()
						{
							public void esegui()
							{
								for(int a=0;a<100&&collegamenti.getVisibility()==View.VISIBLE;a++)ricerca(fip+a);
							}
						};
						new Processo()
						{
							public void esegui()
							{
								for(int a=200;a<256&&collegamenti.getVisibility()==View.VISIBLE;a++)ricerca(fip+a);
							}
						};
						crea.scrivi(schermo().lingua.unisciti);
						nometitolo.scrivi(schermo().lingua.ip);
						nome.scrivi("");
					}
					else
					{
						semetitolo.setVisibility(View.VISIBLE);
						modalita1.setVisibility(View.VISIBLE);
						modalita2.setVisibility(View.VISIBLE);
						collegamenti.setVisibility(View.INVISIBLE);
						seme.setVisibility(View.VISIBLE);
						crea.scrivi(schermo().lingua.crea);
						nometitolo.scrivi(schermo().lingua.nome);
						nome.scrivi("");
						g.aggiorna();
					}
					selezionato2.colore(Color.LTGRAY,Color.DKGRAY);
					selezionato2=b;
					selezionato2.colore(Color.DKGRAY,Color.LTGRAY);
				}
			}
		};
	}
	private View.OnClickListener collegamento(final String ip)
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				new Schermata(schermo(),ip,null);
				cancel();
			}
		};
	}
	private void ricerca(String nuovo)
	{
		try
		{
			Socket s=new Socket();
			s.connect(new InetSocketAddress(nuovo,30001),150);
			toast("ok");
			new Bottone(collegamenti,1,index,9,index+1).scrivi(nuovo).setOnClickListener(collegamento(nuovo));
			index++;
			s.close();
		}
		catch(IOException e)
		{
		}
	}
}
