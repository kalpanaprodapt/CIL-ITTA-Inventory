import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { CLOUD_ACCOUNT_PROVIDER_OPTIONS, CLOUD_ACCOUNT_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { CloudAccountFilter } from './types';

interface Props {
  value: CloudAccountFilter;
  onChange: (next: CloudAccountFilter) => void;
  onReset: () => void;
}

export function CloudAccountFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: CloudAccountFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Cloud Account filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Cloud Account"
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
      <FilterField label="Provider">
        <MultiSelect
          ariaLabel="Provider filter"
          options={CLOUD_ACCOUNT_PROVIDER_OPTIONS}
          value={value.provider ?? []}
          onChange={(v) => patch({ provider: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Account Ref">
        <Input
          aria-label="Account Ref filter"
          value={value.accountRef ?? ''}
          onChange={(v) => patch({ accountRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Subscription Name">
        <Input
          aria-label="Subscription Name filter"
          value={value.subscriptionName ?? ''}
          onChange={(v) => patch({ subscriptionName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Monthly Budget">
        <RangeInputs
          ariaLabel="Monthly Budget"
          min={value.monthlyBudgetMin}
          max={value.monthlyBudgetMax}
          onChange={(min, max) => patch({ monthlyBudgetMin: min, monthlyBudgetMax: max })}
        />
      </FilterField>
      <FilterField label="Environment">
        <Input
          aria-label="Environment filter"
          value={value.environment ?? ''}
          onChange={(v) => patch({ environment: v || undefined })}
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
          options={CLOUD_ACCOUNT_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
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
