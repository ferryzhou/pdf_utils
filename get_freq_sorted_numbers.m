function [ns, freq] = get_freq_sorted_numbers(x)

x = ceil(x);
u = unique(x);
c = zeros(length(u), 1);
for i = 1 : length(u)
    c(i) = sum(x == u(i));
end

[freq, inds] = sort(c, 'descend');
ns = u(inds);

end
