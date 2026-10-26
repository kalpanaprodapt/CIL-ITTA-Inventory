import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { CUSTOMER_ACCOUNT_BILLING_CYCLE_OPTIONS, CUSTOMER_ACCOUNT_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { CustomerAccountFilter } from './types';

interface Props {
  value: CustomerAccountFilter;
  onChange: (next: CustomerAccountFilter) => void;
  onReset: () => void;
}

export function CustomerAccountFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: CustomerAccountFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Customer Account filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Customer Account"
          placeholder="code, name or description"
          value={value.q ?? ''}
          onChange={(v) => patch({ q: v || undefined })}
        />
      </FilterField>
      <FilterField label="Status">
        <MultiSelect
          ariaLabel="Status filter"
          options={LIFECYCLE_OPTIONS}
          value={value.status ?? []}
          onChange={(v) => patch({ status: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Code">
        <Input
          aria-label="Code filter"
          value={value.code ?? ''}
          onChange={(v) => patch({ code: v || undefined })}
        />
      </FilterField>
      <FilterField label="Name">
        <Input
          aria-label="Name filter"
          value={value.name ?? ''}
          onChange={(v) => patch({ name: v || undefined })}
        />
      </FilterField>
      <FilterField label="External Ref">
        <Input
          aria-label="External Ref filter"
          value={value.externalRef ?? ''}
          onChange={(v) => patch({ externalRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Customer Id">
        <Input
          aria-label="Customer Id filter"
          placeholder="UUID"
          value={value.customerId ?? ''}
          onChange={(v) => patch({ customerId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Account Number">
        <Input
          aria-label="Account Number filter"
          value={value.accountNumber ?? ''}
          onChange={(v) => patch({ accountNumber: v || undefined })}
        />
      </FilterField>
      <FilterField label="Billing Cycle">
        <MultiSelect
          ariaLabel="Billing Cycle filter"
          options={CUSTOMER_ACCOUNT_BILLING_CYCLE_OPTIONS}
          value={value.billingCycle ?? []}
          onChange={(v) => patch({ billingCycle: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Currency">
        <Input
          aria-label="Currency filter"
          value={value.currency ?? ''}
          onChange={(v) => patch({ currency: v || undefined })}
        />
      </FilterField>
      <FilterField label="Credit Limit">
        <RangeInputs
          ariaLabel="Credit Limit"
          min={value.creditLimitMin}
          max={value.creditLimitMax}
          onChange={(min, max) => patch({ creditLimitMin: min, creditLimitMax: max })}
        />
      </FilterField>
      <FilterField label="Effective From">
        <DateRange
          ariaLabel="Effective From"
          from={value.effectiveFromFrom}
          to={value.effectiveFromTo}
          onChange={(from, to) => patch({ effectiveFromFrom: from, effectiveFromTo: to })}
        />
      </FilterField>
      <FilterField label="Effective To">
        <DateRange
          ariaLabel="Effective To"
          from={value.effectiveToFrom}
          to={value.effectiveToTo}
          onChange={(from, to) => patch({ effectiveToFrom: from, effectiveToTo: to })}
        />
      </FilterField>
      <FilterField label="Labels">
        <Input
          aria-label="Labels filter"
          value={value.labels ?? ''}
          onChange={(v) => patch({ labels: v || undefined })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={CUSTOMER_ACCOUNT_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Source System">
        <Input
          aria-label="Source System filter"
          value={value.sourceSystem ?? ''}
          onChange={(v) => patch({ sourceSystem: v || undefined })}
        />
      </FilterField>
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
