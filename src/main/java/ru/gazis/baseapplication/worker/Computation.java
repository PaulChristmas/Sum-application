package ru.gazis.baseapplication.worker;

import com.mathworks.toolbox.javabuilder.MWArray;
import com.mathworks.toolbox.javabuilder.MWCharArray;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

import getsum.*;
// import CalcMPCLib.*;

public class Computation {

	private ClassSum sum = null;

	public Computation() {
		try {
			sum = new ClassSum();
		} catch (MWException e) {
			e.printStackTrace();
		}
	}
	
	public void dispose() {
		sum.dispose();
	}

	public double getSum(double aNum, double bNum) throws Exception {
		MWNumericArray a = null;
		MWNumericArray b = null;
		Object result[] = null;

		try {
			a = new MWNumericArray(Double.valueOf(aNum), MWClassID.DOUBLE);
			b = new MWNumericArray(Double.valueOf(bNum), MWClassID.DOUBLE);
			result = sum.getsum(1, a, b);

			return Double.parseDouble(result[0].toString());

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			MWArray.disposeArray(a);
			MWArray.disposeArray(b);
			MWArray.disposeArray(result);
		}

	}
	/*
	public void execMod(String in, String out) throws Exception {
		MWCharArray a = null;
		MWCharArray b = null;
		Object result[] = null;

		try {
			a = new MWCharArray(in);
			b = new MWCharArray(out);
			result = mod.CalcMPCLib(a, b);

			System.err.println(result.toString());

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			MWArray.disposeArray(a);
			MWArray.disposeArray(b);
			MWArray.disposeArray(result);
		}

	}
	*/
}