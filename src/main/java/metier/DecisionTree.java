package metier;

public class DecisionTree {
	private Integer maxDepth;
	private Integer maxBins;
	private Integer minInstancesPerNode;
	
	
	public Integer getMaxDepth() {
		return maxDepth;
	}
	public void setMaxDepth(Integer maxDepth) {
		this.maxDepth = maxDepth;
	}
	public Integer getMaxBins() {
		return maxBins;
	}
	public void setMaxBins(Integer maxBins) {
		this.maxBins = maxBins;
	}
	public Integer getMinInstancesPerNode() {
		return minInstancesPerNode;
	}
	public void setMinInstancesPerNode(Integer minInstancesPerNode) {
		this.minInstancesPerNode = minInstancesPerNode;
	}
	
}
