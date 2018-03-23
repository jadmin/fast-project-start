package #{packagePrefix}#.dubbo;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.github.javaclub.monitor.Monitors;

public class MonitorFilter implements Filter {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		String className = invocation.getInvoker().getInterface().getName();
		String key = "Dubbo:" + className + "." + invocation.getMethodName();
		Monitors.entry(key);
		Result result = null;
		boolean flag = true;
		try {

			result = invoker.invoke(invocation);

		} catch (Exception e) {
			flag = false;
			throw e;
		} finally {
			Monitors.exit(key, flag);
		}

		return result;
	}

}
