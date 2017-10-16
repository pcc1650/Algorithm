// 698 contest 54
public boolean canPartitionKSubsets(int[] nums, int k) {
    int total = 0;
    for(int num: nums)
        total += num;
    if(total % k != 0)
        return false;
    int target = total / k;
    int index = nums.length - 1;
    Arrays.sort(nums);
    if(nums[nums.length - 1] > target)
        return false;
    return search(new int[k], index, nums, target);
}
private boolean search(int[] group, int index, int[] nums, int target) {
    if(index < 0){
        for(int g: group)
            if(g != target)
                return false;
        return true;
    }
    int num = nums[index--];
    for(int i = 0; i < group.length; i++) {
        if(group[i] + num <= target){
            group[i] += num;
            if(search(group, index, nums, target))
                return true;
            group[i] -= num;
        }
        if (group[i] == 0) break;
    }
    return false;
}
