import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { WEBHOOK_SUBSCRIPTION_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { WebhookSubscriptionFilter } from './types';

interface Props {
  value: WebhookSubscriptionFilter;
  onChange: (next: WebhookSubscriptionFilter) => void;
  onReset: () => void;
}

export function WebhookSubscriptionFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: WebhookSubscriptionFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Webhook Subscription filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Webhook Subscription"
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
      <FilterField label="Callback Url">
        <Input
          aria-label="Callback Url filter"
          value={value.callbackUrl ?? ''}
          onChange={(v) => patch({ callbackUrl: v || undefined })}
        />
      </FilterField>
      <FilterField label="Event Types">
        <Input
          aria-label="Event Types filter"
          value={value.eventTypes ?? ''}
          onChange={(v) => patch({ eventTypes: v || undefined })}
        />
      </FilterField>
      <FilterField label="Secret Ref">
        <Input
          aria-label="Secret Ref filter"
          value={value.secretRef ?? ''}
          onChange={(v) => patch({ secretRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Active">
        <TriState
          ariaLabel="Active filter"
          value={value.active}
          onChange={(v) => patch({ active: v })}
        />
      </FilterField>
      <FilterField label="Retry Limit">
        <RangeInputs
          ariaLabel="Retry Limit"
          min={value.retryLimitMin}
          max={value.retryLimitMax}
          onChange={(min, max) => patch({ retryLimitMin: min, retryLimitMax: max })}
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
          options={WEBHOOK_SUBSCRIPTION_LIFECYCLE_STAGE_OPTIONS}
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
