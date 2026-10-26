import { describe, expect, it } from 'vitest';
import { formatDate, formatDecimal, formatNumber, shortId, truncate } from '../utils/format';

describe('format', () => {
  it('formats dates', () => {
    expect(formatDate('2026-03-04')).toContain('2026');
    expect(formatDate(undefined)).toBe('—');
    expect(formatDate('garbage')).toBe('garbage');
  });
  it('formats numbers', () => {
    expect(formatNumber(1234)).toBe('1,234');
    expect(formatDecimal(1.5)).toBe('1.50');
    expect(formatNumber(undefined)).toBe('—');
  });
  it('truncates', () => {
    expect(truncate('abcdef', 4)).toBe('abc…');
    expect(truncate('abc', 4)).toBe('abc');
  });
  it('shortens ids', () => {
    expect(shortId('1234567890')).toBe('12345678…');
  });
});
