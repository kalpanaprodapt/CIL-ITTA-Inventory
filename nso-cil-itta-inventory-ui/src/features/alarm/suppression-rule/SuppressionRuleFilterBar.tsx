import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { SUPPRESSION_RULE_CRITICALITY_OPTIONS } from './types';
import type { SuppressionRuleFilter } from './types';

interface Props {
  value: SuppressionRuleFilter;
  onChange: (next: SuppressionRuleFilter) => void;
  onReset: () => void;
}

export function SuppressionRuleFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: SuppressionRuleFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Suppression Rule filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Suppression Rule"
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
      <FilterField label="Rule Name">
        <Input
          aria-label="Rule Name filter"
          value={value.ruleName ?? ''}
          onChange={(v) => patch({ ruleName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Starts At">
        <DateRange
          ariaLabel="Starts At"
          from={value.startsAtFrom}
          to={value.startsAtTo}
          onChange={(from, to) => patch({ startsAtFrom: from, startsAtTo: to })}
        />
      </FilterField>
      <FilterField label="Ends At">
        <DateRange
          ariaLabel="Ends At"
          from={value.endsAtFrom}
          to={value.endsAtTo}
          onChange={(from, to) => patch({ endsAtFrom: from, endsAtTo: to })}
        />
      </FilterField>
      <FilterField label="Reason">
        <Input
          aria-label="Reason filter"
          value={value.reason ?? ''}
          onChange={(v) => patch({ reason: v || undefined })}
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
          options={SUPPRESSION_RULE_CRITICALITY_OPTIONS}
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
