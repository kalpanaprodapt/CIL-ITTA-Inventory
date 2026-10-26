import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { CONTRACT_CRITICALITY_OPTIONS } from './types';
import type { ContractFilter } from './types';

interface Props {
  value: ContractFilter;
  onChange: (next: ContractFilter) => void;
  onReset: () => void;
}

export function ContractFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: ContractFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Contract filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Contract"
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
      <FilterField label="Contract Number">
        <Input
          aria-label="Contract Number filter"
          value={value.contractNumber ?? ''}
          onChange={(v) => patch({ contractNumber: v || undefined })}
        />
      </FilterField>
      <FilterField label="Start Date">
        <DateRange
          ariaLabel="Start Date"
          from={value.startDateFrom}
          to={value.startDateTo}
          onChange={(from, to) => patch({ startDateFrom: from, startDateTo: to })}
        />
      </FilterField>
      <FilterField label="End Date">
        <DateRange
          ariaLabel="End Date"
          from={value.endDateFrom}
          to={value.endDateTo}
          onChange={(from, to) => patch({ endDateFrom: from, endDateTo: to })}
        />
      </FilterField>
      <FilterField label="Term Months">
        <RangeInputs
          ariaLabel="Term Months"
          min={value.termMonthsMin}
          max={value.termMonthsMax}
          onChange={(min, max) => patch({ termMonthsMin: min, termMonthsMax: max })}
        />
      </FilterField>
      <FilterField label="Auto Renew">
        <TriState
          ariaLabel="Auto Renew filter"
          value={value.autoRenew}
          onChange={(v) => patch({ autoRenew: v })}
        />
      </FilterField>
      <FilterField label="Source System">
        <Input
          aria-label="Source System filter"
          value={value.sourceSystem ?? ''}
          onChange={(v) => patch({ sourceSystem: v || undefined })}
        />
      </FilterField>
      <FilterField label="Region">
        <Input
          aria-label="Region filter"
          value={value.region ?? ''}
          onChange={(v) => patch({ region: v || undefined })}
        />
      </FilterField>
      <FilterField label="Owner">
        <Input
          aria-label="Owner filter"
          value={value.owner ?? ''}
          onChange={(v) => patch({ owner: v || undefined })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={CONTRACT_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Cost Center">
        <Input
          aria-label="Cost Center filter"
          value={value.costCenter ?? ''}
          onChange={(v) => patch({ costCenter: v || undefined })}
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
