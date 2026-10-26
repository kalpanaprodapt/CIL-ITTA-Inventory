const dateFormat = new Intl.DateTimeFormat('en-GB', { dateStyle: 'medium', timeZone: 'UTC' });
const dateTimeFormat = new Intl.DateTimeFormat('en-GB', { dateStyle: 'medium', timeStyle: 'short', timeZone: 'UTC' });
const numberFormat = new Intl.NumberFormat('en-GB');
const decimalFormat = new Intl.NumberFormat('en-GB', { minimumFractionDigits: 2, maximumFractionDigits: 4 });

export function formatDate(value?: string | null): string {
  if (!value) return '—';
  const d = new Date(value);
  return Number.isNaN(d.getTime()) ? value : dateFormat.format(d);
}

export function formatDateTime(value?: string | null): string {
  if (!value) return '—';
  const d = new Date(value);
  return Number.isNaN(d.getTime()) ? value : dateTimeFormat.format(d) + ' UTC';
}

export function formatNumber(value?: number | null): string {
  return value === undefined || value === null ? '—' : numberFormat.format(value);
}

export function formatDecimal(value?: number | null): string {
  return value === undefined || value === null ? '—' : decimalFormat.format(value);
}

export function truncate(value: string | undefined | null, max: number): string {
  if (!value) return '—';
  return value.length > max ? `${value.slice(0, max - 1)}…` : value;
}

export function shortId(value: string): string {
  return value.length > 8 ? `${value.slice(0, 8)}…` : value;
}
