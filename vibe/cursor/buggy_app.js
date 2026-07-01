/**
 * Calculates the tax amount for a given price.
 * @param {number} price - Base price before tax.
 * @param {number} rate - Tax rate as a decimal (for example, 0.1 for 10%).
 * @returns {number} Calculated tax amount.
 * @throws {TypeError} If inputs are not valid numbers.
 * @throws {RangeError} If rate is outside 0 to 1.
 */
function calculateTax(price, rate) {
    if (!Number.isFinite(price) || !Number.isFinite(rate)) {
        throw new TypeError("price and rate must be finite numbers.");
    }
    if (rate < 0 || rate > 1) {
        throw new RangeError("rate must be between 0 and 1.");
    }
    return price * rate;
}

/**
 * Applies a percentage discount to a price.
 * @param {number} price - Original price before discount.
 * @param {number} discountPercent - Discount rate as a decimal.
 * @returns {number} Price after discount is applied.
 * @throws {TypeError} If inputs are not valid numbers.
 * @throws {RangeError} If discountPercent is outside 0 to 1.
 */
function getDiscount(price, discountPercent) {
    if (!Number.isFinite(price) || !Number.isFinite(discountPercent)) {
        throw new TypeError("price and discountPercent must be finite numbers.");
    }
    if (discountPercent < 0 || discountPercent > 1) {
        throw new RangeError("discountPercent must be between 0 and 1.");
    }
    const discount = price * discountPercent;
    return price - discount;
}

/**
 * Formats a numeric value as a USD-style currency string.
 * @param {number|string} price - Value to format.
 * @returns {string} Formatted value with a dollar sign and two decimals.
 * @throws {TypeError} If price cannot be converted to a finite number.
 */
function formatPrice(price) {
    const amount = Number(price);
    if (!Number.isFinite(amount)) {
        throw new TypeError("price must be a finite number or numeric string.");
    }
    return new Intl.NumberFormat("en-US", {
        style: "currency",
        currency: "USD",
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }).format(amount);
}


console.log(calculateTax(100, 0.1));
console.log(getDiscount(100, 0.1));
console.log(formatPrice(100));
console.log(formatPrice(-12.3));
console.log(formatPrice("1234.5"));