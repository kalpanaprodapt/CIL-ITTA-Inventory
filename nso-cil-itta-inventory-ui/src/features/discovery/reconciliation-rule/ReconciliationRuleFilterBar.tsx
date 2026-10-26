import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { RECONCILIATION_RULE_ACTION_OPTIONS, RECONCILIATION_RULE_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { ReconciliationRuleFilter } from './types';

interface Props {
  value: ReconciliationRuleFilter;
  onChange: (next: ReconciliationRuleFilter) => void;
  onReset: () => void;
}

export function ReconciliationRuleFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: ReconciliationRuleFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Reconciliation Rule filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Reconciliation Rule"
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
      <FilterField label="Match Key">
        <Input
          aria-label="Match Key filter"
          value={value.matchKey ?? ''}
          onChange={(v) => patch({ matchKey: v || undefined })}
        />
      </FilterField>
      <FilterField label="Action">
        <MultiSelect
          ariaLabel="Action filter"
          options={RECONCILIATION_RULE_ACTION_OPTIONS}
          value={value.action ?? []}
          onChange={(v) => patch({ action: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Priority">
        <RangeInputs
          ariaLabel="Priority"
          min={value.priorityMin}
          max={value.priorityMax}
          onChange={(min, max) => patch({ priorityMin: min, priorityMax: max })}
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
          options={RECONCILIATION_RULE_LIFECYCLE_STAGE_OPTIONS}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
