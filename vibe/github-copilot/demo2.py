# Function to check if a number is even
def is_even(n):
    return n % 2 == 0

# Function to calculate factorial using recursion
def factorial(n):
    if n < 0:
        return None  # Factorial is not defined for negative numbers
    elif n == 0 or n == 1:
        return 1
    else:
        return n * factorial(n - 1)
    
# Class to represent a bank account with deposit and withdraw methods
class BankAccount:
    def __init__(self, owner, balance=0):
        self.owner = owner
        self.balance = balance

    def deposit(self, amount):
        if amount > 0:
            self.balance += amount
            return f"Deposited {amount}. New balance is {self.balance}."
        else:
            return "Deposit amount must be positive."

    def withdraw(self, amount):
        if amount > self.balance:
            return "Insufficient funds."
        elif amount <= 0:
            return "Withdrawal amount must be positive."
        else:
            self.balance -= amount
            return f"Withdrew {amount}. New balance is {self.balance}."
        
# Function to reverse a string
def reverse_string(s):      
    return s[::-1]

# Shopping cart functions
def add_to_cart(cart, item):
    cart.append(item)
    return cart
def remove_from_cart(cart, item):
    if item in cart:
        cart.remove(item)
        return cart
    else:
        return "Item not found in cart."
    
# Binary search algorithm to find element in sorted array
# Returns index of element if found, else returns -1
def binary_search(arr, target):
    left, right = 0, len(arr) - 1
    while left <= right:
        mid = left + (right - left) // 2
        if arr[mid] == target:
            return mid
        elif arr[mid] < target:
            left = mid + 1
        else:
            right = mid - 1
    return -1

def merge(left: list, right: list) -> list:
    """Merge two sorted lists into one sorted list."""
    result = []
    i = j = 0
    while i < len(left) and j < len(right):
        if left[i] <= right[j]:  # Use <= for stable sort
            result.append(left[i])
            i += 1
        else:
            result.append(right[j])
            j += 1
    result.extend(left[i:])
    result.extend(right[j:])
    return result
# Merge sort algorithm with helper function to merge two sorted halves
def merge_sort(arr: list) -> list:
    """Sort a list using iterative merge sort."""
    if not isinstance(arr, list):
        raise TypeError("Input must be a list")
    if len(arr) <= 1:
        return arr[:]
    
    # Iterative approach: start with subarrays of size 1, then merge progressively
    # This avoids recursion depth limits for large arrays
    size = 1  # Initial size of subarrays (starts with individual elements)
    while size < len(arr):
        # Loop through the array in steps of size*2 to process pairs of subarrays
        for start in range(0, len(arr), size * 2):
            # Calculate the midpoint and end of the current pair of subarrays
            mid = min(start + size, len(arr))  # End of first subarray
            end = min(start + size * 2, len(arr))  # End of second subarray
            
            # Extract the left and right halves to merge
            left = arr[start:mid]
            right = arr[mid:end]
            
            # Merge the two sorted halves using the helper function
            merged = merge(left, right)
            
            # Replace the original section of the array with the merged result
            arr[start:start + len(merged)] = merged
        
        # Double the subarray size for the next pass
        size *= 2
    
    # Return the fully sorted array
    return arr

print(merge_sort([3, 1, 4, 1, 5, 9, 2, 6]))